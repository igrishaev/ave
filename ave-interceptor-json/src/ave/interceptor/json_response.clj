(ns ave.interceptor.json-response
  (:require
   [integrant.core :as ig]
   [ring.middleware.json :as json]

   [clojure.spec.alpha :as s]))


(defmethod ig/init-key ::*
  [_ options]

  {:name ::interceptor
   :leave
   (fn [ctx]
     (update ctx :response json/json-response options))})


(defmethod ig/pre-init-spec ::* [_]
  (s/nilable ::config))


(s/def ::config
  (s/keys :opt-un [::key-fn
                   ::pretty
                   ::escape-non-ascii
                   ::stream?]))


(s/def ::key-fn ifn?)
(s/def ::pretty boolean?)
(s/def ::escape-non-ascii boolean?)
(s/def ::stream? boolean?)
