(ns ave.interceptor.keyword-params
  (:require
   [integrant.core :as ig]
   [ring.middleware.keyword-params :as kw]

   [clojure.spec.alpha :as s]))


(defmethod ig/init-key ::*
  [_ options]
  {:name ::interceptor
   :enter
   (fn [ctx]
     (update ctx :request kw/keyword-params-request options))})


(defmethod ig/pre-init-spec ::* [_]
  (s/nilable ::config))


(s/def ::config
  (s/keys :opt-un [::parse-namespaces?]))


(s/def ::parse-namespaces? boolean?)
