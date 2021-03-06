(ns roam-translate.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [clj-http.client :as client]
            [environ.core :refer [env]]
            [clojure.walk :as walk]
            [clojure.data.json :as json]
            [ring.util.response :refer [response]]
            [ring.middleware.cors :refer [wrap-cors]]
            [ring.middleware.json :refer [wrap-json-body wrap-json-response]]))

(def apikey (env :apikey))
(def watson (env :watson))

(defn translate [{:keys [text lang]}]
  (try
    (let [resp (client/post
                watson
                {:content-type
                 :json
                 :basic-auth ["apikey" apikey]
                 :body (json/write-str {:text text
                                        :model_id lang})})]
      ; Success
      {:success true
       :result (-> (json/read-str (resp :body) :key-fn keyword)
                   :translations
                   first
                   :translation)})
    ; Error
    (catch Exception e
      {:success false
       :result nil})))

(defroutes app-routes
  (POST "/translate" {body :body}
    (response
     (translate (walk/keywordize-keys body))))
  (route/not-found nil))

; Wrap middleware
(def app
  (-> app-routes
      wrap-json-response
      wrap-json-body
      (wrap-cors
       :access-control-allow-origin [#"https://roamresearch.com/"]
       :access-control-allow-methods [:post])))
