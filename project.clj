(defproject roam-translate "0.1.0-SNAPSHOT"
  :description "Roam Translate demo API"
  :url ""
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [clj-http "3.12.0"]
                 [compojure "1.6.1"]
                 [environ "1.2.0"]
                 [org.clojure/data.json "1.0.0"]
                 [ring/ring-json "0.5.0"]
                 [ring-cors "0.1.13"]
                 [ring/ring-defaults "0.3.2"]]
  :plugins [[lein-ring "0.12.5"]
            [lein-environ "1.2.0"]]
  :ring {:handler roam-translate.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.2"]]}})
