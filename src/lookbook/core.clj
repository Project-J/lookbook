(ns lookbook.core)

(def base 5)

(def directions {"" ""
                 "l" "-left"
                 "r" "-right"
                 "t" "-top"
                 "b" "-bottom"
                 "y" "-vertical"
                 "x" "-horizontal"})

(def colours {"black" "#111"
              "gray" "#aaa"
              "silver" "#ddd"
              "white" "#fff"
              "aqua" "#7fdbff"
              "blue" "#0074d9"
              "navy" "#001f3f"
              "teal" "#39cccc"
              "green" "#2ecc40"
              "olive" "#3d9970"
              "lime" "#01ff70"
              "yellow" "#ffdc00"
              "orange" "#ff851b"
              "red" "#ff4136"
              "fuchsia" "#f012be"
              "purple" "#b10dc9"
              "maroon" "#85144b"})

(defn- gen-variants
  ([name abbrev values]
    (apply merge (map #(hash-map (keyword (str abbrev (key %)))
                        {name (val %)})
                   values)))

  ([property abbrev value dirs]
    (apply merge (map #(hash-map (keyword (str abbrev (key %) value))
                        {(keyword (str (name property) (val %))) value})
                   dirs))))


;; COLOURS & OPACITY

(def colours-map (apply merge [(gen-variants :background-color "bg-" colours)
                               (gen-variants :border-color "br-" colours)
                               (gen-variants :color "" colours)
                               (gen-variants :text-decoration-color "ul-" colours)
                               {:transparent {:opacity 0}}
                               {:muted {:opacity 0.5}}]))


;; WHITESPACES

;; Paddings (e.g. px5 = padding-horizontal: 5px, increments of five)
(def paddings (apply merge (map
                             #(gen-variants :padding "p" % directions)
                             (range 0 (* base 10) base))))

;; Margins (e.g. mt15 = margin-top: 15, increments of five)
(def margins (apply merge (map
                            #(gen-variants :margin "m" % directions)
                            (range 0 (* base 10) base))))


;; TYPOGRAPHY

(def typograpy {:bold {:font-weight "bold"}
                :italic {:font-style "italic"}
                :underline {:text-decoration-line "underline"}
                :align-center {:text-align "center"}
                :align-right {:text-align "right"}
                :align-left {:text-align "left"}
                :t10 {:font-size 10}
                :t12 {:font-size 12}
                :t14 {:font-size 14}
                :t16 {:font-size 16}
                :t18 {:font-size 18}})


;; FLEXBOX

(def flex
  {; Parent
   :f-column {:flex-direction "column"}
   :f-row {:flex-direction "row"}
   :f-wrap {:flex-wrap "wrap"}
   :f-center {:align-items "center"}
   :f-stretch {:align-items "stretch"}
   :f-justify {:justify-content "space-between"}
   :f-start {:align-items "flex-start"}
   :f-end {:align-items "flex-end"}

   ; Child
   :fit {:flex 1}
   :f1 {:flex 1}
   :f-self-center {:align-self "center"}})


(defn styles []
  (merge colours-map margins paddings custom flex typograpy))

(defn style [key]
  (get (styles) key))


(defmacro s
  "takes custom styles in the form of a map, existing styles in the form of
   a keyword or references to styles as symbols.

   for example:
   ```
   [view {:style (c :fit :bg-red :f-justify {:paddingTop 123})}]
   ```
   "
  [& xs]
  (let [[m exprs] (reduce
                    (fn [[m exprs] x]
                      (cond
                        (keyword? x) (if-let [s (style x)]
                                       [(merge m s) exprs]
                                       [m (conj exprs `(style ~x))])
                        (map? x) [(merge m x) exprs]
                        :else [m (conj exprs x)]))
                    [{} []]
                    xs)]
    (if (seq exprs)
      `(merge ~m ~@exprs)
      m)))
