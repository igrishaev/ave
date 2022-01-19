(ns ave.interceptor.json.response.spec
  (:require
   [clojure.spec.alpha :as s]))


(s/def ::config
  (s/keys :opt-un [::key-fn
                   ::pretty
                   ::escape-non-ascii
                   ::stream?]))


(s/def ::key-fn ifn?)
(s/def ::pretty boolean?)
(s/def ::escape-non-ascii boolean?)
(s/def ::stream? boolean?)
