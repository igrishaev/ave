(ns ave.router.reitit
  (:require
   [ave.router.reitit.spec :as spec]

   [reitit.core :as r]
   [integrant.core :as ig]))


(defmethod ig/init-key ::ig
  [_ {:keys [routes
             command->handler
             command-not-found]}]

  (let [router
        (r/router routes)]

    (fn [{:keys [request]}]

      (let [{:keys [uri
                    request-method]}
            request

            {:keys [data
                    path
                    path-params]}
            (r/match-by-path router uri)

            command
            (or
             (get data :any)
             (get data request-method)
             command-not-found
             :handler/not-found)

            handler
            (or
             (get command->handler command)
             (throw
              (ex-info "Reitit handler not found"
                       {:ex/type ::handler-not-found
                        :http/status 404
                        :reitit/command command
                        :reitit/data data})))

            request*
            (assoc request
                   :command command
                   :path path
                   :path-params path-params)]

        (handler request*)))))


(defmethod ig/pre-init-spec ::ig [_]
  ::spec/config)
