#/usr/bin/bash

GATK="/tools/GATK/GenomeAnalysisTK.jar"

java -jar $GATK -S qsl.scala -R /haplox/ref/GATK/ucsc.hg19/ucsc.hg19.fasta -I /haplox/rawfq/160204/160126_E00300_0094_BHKNLLCCXX/good/20160204-ffpegDNA-028_S28_L001_R2_001.good.fq
