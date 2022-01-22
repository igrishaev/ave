(ns ave.logging.unilog
  (:require
   [integrant.core :as ig]
   [unilog.config :as unilog]))


(def defaults
  {:level :info
   :console true
   :overrides {:ave :debug}})


(defmethod ig/init-key ::ig
  [_ params]
  (unilog/start-logging! (or params defaults)))
