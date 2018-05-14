package services

import scala.concurrent.Future
import scala.util.control.NonFatal
import scala.concurrent.ExecutionContext.Implicits._

trait RandomNumberService {
  def generateRandomNumber: Future[Int]
}

trait DiceService {
  def throwDice: Future[Int]
}

class DiceDrivenRandomNumberService(dice: DiceService) extends RandomNumberService {
  override def generateRandomNumber: Future[Int] =
    dice.throwDice
    .recoverWith {
      case NonFatal(t) => generateRandomNumber
    }
}

class RollingDiceService extends DiceService {
  override def throwDice: Future[Int] = Future.successful(4)
}

