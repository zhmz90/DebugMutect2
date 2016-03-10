
import org.broadinstitute.gatk.queue.QScript
import org.broadinstitute.gatk.queue.extensions.gatk._

class PipeLine extends QScript {


  def script() {

    add(new CommandLineFunctions { def commandline = "echo hello world"})
  }


}


