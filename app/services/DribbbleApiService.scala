package services

import models._
import play.api.Play.current
import play.api.libs.json._
import play.api.libs.ws._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class DribbbleApiService {

  private def request(requestUrl: String) = {
    WS.url(s"https://api.dribbble.com/v1/$requestUrl")
      .withHeaders(("Authorization", "Bearer b885be47e9c2e28d25ca7f54443fedbf4fd05c74684bf4f65a30ebfc1f55b481"))
      .get()
  }

  def getUserFollowers(userName: String): Future[List[User]] = {
    import formats.UserFormats.userFollowersFormat
    request(s"users/$userName/followers").map {
      response => response.json.validate[List[User]]
    }.map {
      jsResult => jsResult match {
        case s: JsSuccess[List[User]] => s.get
        case e: JsError => Nil  // todo: analyze error (rate limit), custom exception
      }
    }
  }

  def getUserShots(userName: String): Future[List[Shot]] = {
    import formats.ShotFormats.userShotsFormat
    request(s"users/$userName/shots").map {
      response => response.json.validate[List[Shot]]
    }.map {
      jsResult => jsResult match {
        case s: JsSuccess[List[Shot]] => s.get
        case e: JsError => Nil  // todo: analyze error (rate limit), custom exception
      }
    }
  }

  def getShotLikers(shotId: Integer): Future[List[User]] = {
    import formats.UserFormats.shotLikersFormat
    request(s"shots/$shotId/likes").map {
      response => response.json.validate[List[User]]
    }.map {
      jsResult => jsResult match {
        case s: JsSuccess[List[User]] => s.get
        case e: JsError => Nil  // todo: analyze error (rate limit), custom exception
      }
    }
  }

}
