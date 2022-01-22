(ns ave.demo.handler.help
  (:require
   [integrant.core :as ig]))


(defmethod ig/init-key ::ig
  [_ _]
  (fn [ctx]
    {:status 200
     :headers {"content-type" "text/plain"}
     :body "This is jus a help page."}))
