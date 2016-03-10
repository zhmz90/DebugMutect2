
import org.broadinstitute.gatk.tools.walkers.cancer.m2

//class HM2 extends m2.MuTect2 {
class HM2 extends m2.Mutect2 {

  def hello(){
    println("Hello World")
  }

}

object test{
  HM2  h
  h.hello()
}
