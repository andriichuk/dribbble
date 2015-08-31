package apis

import javax.inject.Inject

import modelViews.UserLikes
import formats.UserLikesFormats._
import services.DribbbleApiService
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.libs.json._
import play.api.mvc._
import scalaz._
import scalaz.Scalaz._
import scalaz.ListT._

import scala.concurrent.Future


class StatsApi  @Inject() (dribbbleService: DribbbleApiService)
  extends Controller {

  def topTen(userName: String) = Action.async {

    val dataRequestFuture = (for {
      followers <- listT(dribbbleService.getUserFollowers(userName))
      shots <- listT(dribbbleService.getUserShots(followers.name))
      likers <- listT(dribbbleService.getShotLikers(shots.id))
    } yield {
        likers
    }).run

    dataRequestFuture.map {
      case likers => {
        val topLikers = likers.groupBy(identity).mapValues(_.size).toList.sortBy(-_._2).take(10).map {
          case (user, likes) => UserLikes(user.name, likes)
        }
        Ok(Json.toJson(topLikers))
      }
    }.recover {
      case e: Throwable => InternalServerError(Json.obj("error" -> e.getMessage())) // TO DO: log exception
    }
  }

}
