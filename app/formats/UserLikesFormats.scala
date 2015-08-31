package formats


import modelViews.UserLikes
import play.api.libs.functional.syntax._
import play.api.libs.json._

object UserLikesFormats {

  implicit val userWrites: Writes[UserLikes] = (
    (JsPath \ "username").write[String] and
    (JsPath \ "likes").write[Int]
  )(unlift(UserLikes.unapply))

}
