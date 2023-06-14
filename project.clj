(defproject graphnotes "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "MIT"
            :url "https://opensource.org/license/mit/"}
  :dependencies [
    [org.clojure/clojure "1.11.1"]
    [org.clojure/tools.cli "1.0.214"]
    [metosin/muuntaja "0.6.8"]
    [metosin/reitit "0.6.0"]
    [ring "1.10.0"]
    [http-kit "2.3.0"]
    [com.github.full-spectrum/neo4clj-core "1.1.0"]
    [buddy/buddy-hashers "1.8.158"]
    [re-frame "1.2.0"]]
    [reagent "1.1.1"]
    [cljs-ajax "0.8.4"]
  :main ^:skip-aot graphnotes.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
