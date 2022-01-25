(ns ave.interceptor.request-id
  (:require
   [integrant.core :as ig]
   [clojure.spec.alpha :as s])
  (:import java.util.UUID))


(defn gen-uuid [_request]
  (str (UUID/randomUUID)))


(defmethod ig/init-key ::*
  [_ _]

  {:name ::interceptor
   :enter
   (fn [{:as ctx :keys [request]}]
     (let [request-id
           (or (get-in request [:headers "x-request-id"])
               (gen-uuid request))]
       (assoc ctx :request-id request-id)))

   :leave
   (fn [{:as ctx :keys [request-id]}]
     (assoc-in ctx
               [:response :headers "x-request-id"]
               request-id))})


#_
(defmethod ig/pre-init-spec ::* [_]
  ::config)
