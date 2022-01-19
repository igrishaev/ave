(ns ave.interceptor.json.body.spec
  (:require
   [clojure.spec.alpha :as s]))


(s/def ::config
  (s/keys :opt-un [::key-fn
                   ::keywords?
                   ::bigdecimals?]))

(s/def ::key-fn ifn?)
(s/def ::keywords? boolean?)
(s/def ::bigdecimals? boolean?)
