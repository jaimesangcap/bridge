(ns bridge.main
  (:require bridge.ui
            [bridge.ui.frame :as ui.frame]
            bridge.event.ui
            [bridge.ui.routes :as ui.routes]
            [cljs.reader :as edn]
            [reagent.core :as r]
            [re-frame.core :as rf]))

(rf/reg-event-db ::initialize
  (fn [_ _]
    (merge (some-> (js/document.getElementById "initial-state")
                   .-textContent
                   edn/read-string)
           bridge.ui/initial-state)))

(defn ^:export refresh []
  (rf/dispatch-sync [::initialize])
  (ui.routes/app-routes)
  (r/render [ui.frame/app] (js/document.getElementById "mount")))
