(ns lookbook.core
  (:require-macros [lookbook.core [c styles]]))

(def react (js/require "react-native/Libraries/react-native/react-native.js"))
(def viewport-dimensions (-> react .-Dimensions (.get "window") (js->clj :keywordize-keys true)))
(def pixel-ratio (-> react .-PixelRatio .get))


(defn layout []
  (let [{:keys [width height]} viewport-dimensions]
    {:relative {:position "relative"}
     :absolute {:position "absolute"}
     :bottom-0 {:bottom 0}
     :top-0 {:top 0}
     :right-0 {:right 0}
     :left-0 {:left 0}
     :full-width {:width width}
     :full-square {:width width
                   :height width}
     :full-height {:height height}
     :half-square {:width (- (/ width 2) 25)
                   :height (- (/ width 2) 25)}
     :half-width {:width (/ width 2)}
     :half-height {:height (/ height 2)}
     :brw-1half {:border-width (if (> pixel-ratio 2) 2 1.5)}}))


(defn styles [] (layout))

(defn style [key]
  (get (styles) key))
