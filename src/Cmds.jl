module Cmds

const data_dir = "../data"
const tumor  = joinpath(data_dir, "069_cfdna_rg.bam")
const normal = joinpath(data_dir, "069_gdna_rg.bam")
const ref = joinpath(data_dir, "ucsc.hg19", "ucsc.hg19.fasta")
const GATK_jar = joinpath(homedir(), "haplox/Github/gatk-protected/target/GenomeAnalysisTK.jar")
const out_var = joinpath(data_dir, "variant.vcf") 

function M2()
    cmd = `java -jar $(GATK_jar) -T MuTect2 -R $ref -I:tumor $tumor -I:normal $normal -o $out_var`

    @show cmd
    run(cmd)
end





end
