(ns ave.interceptor.keyword-params
  (:require
   [ring.middleware.keyword-params :as kw]
   [integrant.core :as ig]

   [clojure.spec.alpha :as s]))


(defn make [& options]
  {:name ::interceptor
   :enter
   (fn [ctx]
     (update ctx :request kw/keyword-params-request options))})


(def default
  (make))


(defmethod ig/init-key ::ig
  [_ options]
  (make options))


(defmethod ig/pre-init-spec ::ig [_]
  ::config)


(s/def ::config
  (s/keys :opt-un [::parse-namespaces?]))


(s/def ::parse-namespaces? boolean?)
