# Lookbook

Lookbook is a set of utility styles built with use for React Native in mind. It allows you to easily compose styles for your components. You can see it as Basscss for React Native. All static styles will be pre-compiled during Clojurescript compilation for maximum performance.

## Usage

Drop the dependency in your `build.boot` or `project.clj`:
```
[co.iamfy/lookbook "0.1.0-SNAPSHOT"]
```

Require in namespace:
```
(:require [lookbook.core :as lookbook :refer-macros [s styles]])
```

Use in view:
```
[view {:style (c :bg-red :full-height {:width 30})}]
;; c outputs {:background-color "#ff4136" :height 700 :width 30}
```

## BassCSS
Lookbook is currently approx. 50% compatible with Basscss. We took the liberty to change somethings to be more useful with React Native.

Things which are different:
- raw pixel values for layouting instead of Basscss's 4 steps. `:p15` is `padding: 15`
- some of the naming around flexbox is different
- no `h1`,`h2`, etc, but `:t10` till `:t18` in increments of 2 pixels.

Things which are still missing:
- [ ] borders
- [ ] typography
- [ ] buttons
- [ ] forms

## TODO: Responsiveness
We're planning to add some helpers to aid screen size dependent styling which is currently hard to do in React Native. See the open issue.

## Javascript
Currently Lookbook's slick syntax is only supported by Clojurescript, but it shouldn't be hard to make a small api to make it work (without pre-compilation unfortunately) for Javascript as well. Not planning to do this unless there's enough interest.

## Maintenaince/Production use
[Fy](https://www.iamfy.co) uses a slightly modified (different colours) version of this library in [production](https://appsto.re/gb/5KjH7.i) and actively maintains it. Pull requests and contributions are more than welcome!
