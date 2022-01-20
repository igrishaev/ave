(ns ave.interceptor.util)


(defn handler->interceptor
  [handler]
  {:enter
   (fn [ctx]
     (assoc ctx :response (handler ctx)))})


(def interceptor-leave-response
  {:leave :response})


(defn wrap-stack
  [interceptors handler]
  (-> [interceptor-leave-response]
      (into interceptors)
      (conj (handler->interceptor handler))))
