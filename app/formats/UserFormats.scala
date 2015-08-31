package formats

import models._
import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._

object UserFormats  {

  implicit val userFollowersFormat: Reads[User] = (
    (JsPath \ "follower" \ "id").read[Int](min(1)) and
      (JsPath \ "follower" \ "username").read[String](minLength[String](1))
    )(User.apply _)

  implicit val shotLikersFormat: Reads[User] = (
    (JsPath \ "user" \ "id").read[Int](min(1)) and
      (JsPath \ "user" \ "username").read[String](minLength[String](1))
    )(User.apply _)

}
