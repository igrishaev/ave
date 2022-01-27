(defproject com.github.igrishaev/ave-core "0.1.0-SNAPSHOT"

  :description
  "Core parts of the Ave framework"

  :plugins [[lein-parent "0.3.8"]]

  :parent-project
  {:path "../project.clj"
   :inherit [:deploy-repositories
             :license
             :managed-dependencies
             :plugins
             :repositories
             :scm
             :test-selectors
             :url
             [:profiles :dev]]}

  :profiles
  {:dev
   {:resource-paths ["../dev-resources"]
    :dependencies
    [[ch.qos.logback/logback-classic]]}}

  :dependencies
  [[aero]
   [integrant]
   [spootnik/signal]
   [org.clojure/tools.logging]])
