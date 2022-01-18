(ns ave.server.jetty.spec
  (:require
   [clojure.spec.alpha :as s]))


(s/def ::config
  (s/keys :req-un [::params
                   ::handler]
          :opt-un [::interceptors]))


(s/def ::params
  map?)


(s/def ::handler
  fn?)


(s/def ::interceptors
  (s/coll-of ::interceptor))


(s/def ::interceptor
  fn?)
