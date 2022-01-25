(ns ave.server-jetty
  (:require
   [integrant.core :as ig]
   [ring.adapter.jetty :as jetty]

   [clojure.tools.logging :as log]
   [clojure.spec.alpha :as s]))


(def defaults
  {:port 8080})


(def overrides
  {:join? false})


(defmethod ig/init-key ::*
  [_ {:as params
      :keys [port
             handler]}]

  (let [params*
        (-> defaults
            (merge params)
            (merge overrides))

        server
        (jetty/run-jetty handler params*)]

    (log/infof "Jetty server has been started, port %s" port)

    server))


(defmethod ig/halt-key! ::*
  [_ ^org.eclipse.jetty.server.Server server]
  (.stop server)
  (log/infof "Jetty server has been stopped"))


(defmethod ig/pre-init-spec ::* [_]
  ::config)


(s/def ::config
  (s/keys :req-un [::port
                   ::handler]))


(s/def ::port int?)

(s/def ::handler fn?)
