(ns ave.interceptor.json.params.spec
  (:require
   [clojure.spec.alpha :as s]))


(s/def ::config
  (s/keys :opt-un [::key-fn
                   ::keywords?]))

(s/def ::key-fn ifn?)
(s/def ::keywords? boolean?)
