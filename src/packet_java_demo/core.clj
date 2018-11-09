(ns packet-java-demo.core
  (:import  (net.packet BillingCycle)
            (net.packet.impl PacketClient)
            (net.packet.pojo Device
                             OperatingSystem
                             Facility
                             Plan))
  (:require [clojure.java.io :as io]
            [clojure.tools.cli :refer [parse-opts]]
            [taoensso.timbre :as timbre]
            [taoensso.timbre.appenders.core :as appenders]
            )
  (:gen-class))

(timbre/refer-timbre)

(def proj-id "6b411677-07ed-4172-b351-3798dbc7ca39"); VZ Demo Project

(def user-data (io/resource "cloud-config"))
(def token-resource (io/resource "token"))
(def auth-token (slurp token-resource))

(defn create-t0-device [name os facility]
  (let [plan (Plan. "baremetal_0")
        billing-cycle (BillingCycle/fromValue "hourly")
        operating-system (OperatingSystem. os)
        facility (Facility. facility)]
    (Device. name plan billing-cycle operating-system facility)))

(defn launch-server [auth-token project-id name os facility]
  (let [api    (PacketClient. auth-token "1") ; pass auth-token and API version
        device (doto
                (create-t0-device name os facility)
                 (.setUserdata (slurp user-data)))]
    (try
      (let [response (.createDevice api project-id device)
            id       (.getId response)]
        id)
      (catch Exception e
        (println (str "Received error " e))))))

(def cli-options
  [["-h" "--help" "Print this help"
    :default false]
   ["-t" "--host HOST" "Specify hostname"
    :default false]
   ["-o" "--os OS" "Specify valid OS"
    :default "ubuntu_16_04"]
   ["-f" "--facility FACILITY" "Specify facility"
    :default "ewr1"]])

(defn -main [& args]
  (let [{:keys [options arguments errors summary]} (parse-opts args cli-options)]
    ;{:help false, :host false, :os "foo", :facility "ewr1"}
    (cond
      (:help options)        (println summary)
      errors                 (println "Something went wrong")
      (nil? (:host options)) (println "Please specify a hostname"))
    (let [{:keys [help host os facility]} options
          id                              (launch-server auth-token proj-id host os facility)]
      (println id))))
