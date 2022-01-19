(ns ave.server.jetty
  (:require
   [ave.interceptor :as ai]
   [ave.server.jetty.spec :as spec]

   [exoscale.interceptor :as ei]
   [ring.adapter.jetty :as jetty]
   [integrant.core :as ig]

   [clojure.tools.logging :as log]))


(def defaults
  {:port 8080})


(def overrides
  {:join? false})


(defmethod ig/init-key ::ig
  [_ {:keys [params
             handler
             interceptors]}]

  (let [stack
        (ai/wrap-stack interceptors handler)

        -handler
        (fn [request]
          (ei/execute {:request request} stack))

        params*
        (-> defaults
            (merge params)
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
  ::spec/config)
