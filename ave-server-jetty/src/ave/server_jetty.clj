(ns ave.server-jetty
  (:require
   [ave.interceptor :as ai]

   [exoscale.interceptor :as ei]
   [ring.adapter.jetty :as jetty]
   [integrant.core :as ig]

   [clojure.spec.alpha :as s]
   [clojure.tools.logging :as log]))


(def defaults
  {:port 8080})


(def overrides
  {:join? false})


(defmethod ig/init-key ::ig
  [_ {:as jetty-params
      :keys [handler
             interceptors]}]

  (let [stack
        (ai/wrap-stack interceptors handler)

        -handler
        (fn [request]
          (ei/execute {:request request} stack))

        params*
        (-> defaults
            (merge jetty-params)
            (merge overrides))

        {:keys [port]}
        params*

        server
        (jetty/run-jetty -handler params*)]

    (log/infof "Jetty server has been started on port %s" port)

    server))


(defmethod ig/halt-key! ::ig
  [_ ^org.eclipse.jetty.server.Server server]
  (.stop server)
  (log/info "Jetty server has been stopped"))


(defmethod ig/pre-init-spec ::ig [_]
  ::config)


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
