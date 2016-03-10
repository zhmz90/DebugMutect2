
import org.broadinstitute.gatk.queue.QScript
import org.broadinstitute.gatk.queue.extensions.gatk._

class PipeLine extends QScript {
/*
  @Input(doc="The reference default=/haplox/ref/GATK/ucsc.hg19/ucsc.hg19.fasta",shortName="R")
  var referenceFile: File = "/haplox/ref/GATK/ucsc.hg19/ucsc.hg19.fasta"

  @Input(doc="parired or single fastq files", shortName="I")
  var fastqFiles: List[File] = Nil

  @Output
  var alignOut: File = _
 */
  def script() {
/*
    var align = new BWA
    align.referenceFile = referenceFile
    align.input_file = fastqFiles
    align.out = alignOut

    add(align)
 */
    add(new CommandLineFunction {def commandLine = "echo hello world"})
  }


}


