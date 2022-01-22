(ns ave.demo.handler.help
  (:require
   clojure.pprint
   [integrant.core :as ig]))


(defmethod ig/init-key ::ig
  [_ _]
  (fn [ctx]
    {:status 200
     :headers {"content-type" "text/plain"}
     :body (with-out-str
             (clojure.pprint/pprint ctx))}))
