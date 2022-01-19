(ns ave.router.reitit.spec
  (:require
   [clojure.spec.alpha :as s]))


(s/def ::config
  (s/keys :req-un [::routes
                   ::command->handler
                   ::command-not-found]))

(s/def ::command keyword?)

(s/def ::routes vector?)

(s/def ::command->handler
  (s/map-of ::command fn?))

(s/def ::command-not-found ::command)
