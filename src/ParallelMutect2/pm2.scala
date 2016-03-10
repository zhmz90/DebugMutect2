import java.io.File

import org.broadinstitute.gatk.queue.QScript
import org.broadinstitute.gatk.queue.extensions.gatk._
import org.broadinstitute.gatk.queue.function.CommandLineFunction
import org.broadinstitute.gatk.queue.util.QScriptUtils
import org.broadinstitute.gatk.utils.commandline.{Input, Output}
import org.broadinstitute.gatk.utils.variant.GATKVariantContextUtils.FilteredRecordMergeType

import scala.collection.mutable.ListBuffer

class create_M2_pon extends QScript {

  @Argument(shortName = "bams", required = true, doc = "file of all BAM files")
  var allBams: String = ""

  @Argument(shortName = "o", required = true, doc = "Output prefix")
  var outputPrefix: String = ""

  @Argument(shortName = "minN", required = false, doc = "minimum number of sample observations to include in PON")
  var minN: Int = 1

  @Argument(doc="Reference fasta file to process with", fullName="reference", shortName="R", required=false)
  var reference = new File("/haplox/ref/GATK/ucsc.hg19/ucsc.hg19.fasta")

  @Argument(doc="Intervals file to process with", fullName="intervals", shortName="L", required=true)
  var intervals : File = ""

  @Argument(shortName = "sc", required = false, doc = "base scatter count")
  var scatter: Int = 70


  def script() {
    val bams = QScriptUtils.createSeqFromFile(allBams)
    val genotypesVcf = outputPrefix + ".genotypes.vcf"
    val finalVcf = outputPrefix + ".vcf"

    val perSampleVcfs = new ListBuffer[File]()
    for (bam <- bams) {
      val outputVcf = "sample-vcfs/" + bam.getName + ".vcf"
      add( createM2Config(bam, outputVcf))
      perSampleVcfs += outputVcf
    }

    val cv = new CombineVariants()
    cv.reference_sequence = reference
    cv.memoryLimit = 2
    cv.setKey = "null"
    cv.minimumN = minN
    cv.memoryLimit = 16
    cv.filteredrecordsmergetype = FilteredRecordMergeType.KEEP_IF_ANY_UNFILTERED
    cv.filteredAreUncalled = true
    cv.variant = perSampleVcfs
    cv.out = genotypesVcf

    // using this instead of "sites_only" because we want to keep the AC info
    val vc = new VcfCutter()
    vc.inVcf = genotypesVcf
    vc.outVcf = finalVcf

    add (cv, vc)

  }


  def createM2Config(bam : File, outputVcf : File): org.broadinstitute.gatk.queue.extensions.gatk.MuTect2 = {
    val mutect2 = new org.broadinstitute.gatk.queue.extensions.gatk.MuTect2

    mutect2.reference_sequence = reference
    mutect2.artifact_detection_mode = true
    mutect2.intervalsString :+= intervals
    mutect2.memoryLimit = 2
    mutect2.input_file = List(new TaggedFile(bam, "tumor"))

    mutect2.scatterCount = scatter
    mutect2.out = outputVcf

    mutect2
  }
}

class VcfCutter extends CommandLineFunction {
  @Input(doc = "vcf to cut") var inVcf: File = _
  @Output(doc = "output vcf") var outVcf: File = _

  def commandLine = "cat %s | cut -f1-8 > %s".format(inVcf, outVcf)
}
