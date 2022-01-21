(ns ave.logging.unilog
  (:require
   [integrant.core :as ig]
   [unilog.config :as unilog]))


(defmethod ig/init-key ::ig
  [_ params]
  (unilog/start-logging! params))
