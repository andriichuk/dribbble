package formats

import models._
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json._

object ShotFormats {

  implicit val userShotsFormat: Reads[Shot] = (
    (JsPath \\ "id").read[Int](min(1)) and
      (JsPath \\ "title").read[String](minLength[String](1))
    )(Shot.apply _)

}
