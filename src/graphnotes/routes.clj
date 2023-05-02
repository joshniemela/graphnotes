(ns graphnotes.routes)


(def ping-routes
  ["/ping" {:name :ping
            :get (fn [_]
                   {:status 200
                    :body {:ping "pong"}})}])

;
;       ["/get" {:get {:parameters {:query {:x int?, :y int?}}
;                       :responses  {200 {:body {:total int?}}}
;                       :handler    (fn [{{{:keys [x y]} :query} :parameters}]
;                                     {:status 200
;                                      :body   {:total (+ x y)}})}}]
;       ["/post" {:post {:parameters {:query {:x int?}
;                                     :body   {:y int?}}
;                        :responses  {200 {:body {:total int?}}}
;                        :handler    (fn [{{{:keys [x]} :query} :parameters
;                                          {{:keys [y]} :body} :parameters}]
;                                      {:status 200
;                                       :body   {:total (+ x y)}})}}]
;       ["/plus/:z" {:post {:parameters {:query {:x int?}
;                                        :body {:y int?}
;                                        :path {:z int?}}
;                           :responses {200 {:body {:total int?}}}
;                           :handler (fn [{:keys [parameters]}]
;                                      (let [total (+ (-> parameters :query :x)
;                                                     (-> parameters :body :y)
;                                                     (-> parameters :path :z))]
;                                        {:status 200
;                                         :body {:total total}}))}}]]
;
;       ["/get" {:get {:parameters {:query {:x int?, :y int?}}
;                       :responses  {200 {:body {:total int?}}}
;                       :handler    (fn [{{{:keys [x y]} :query} :parameters}]
;                                     {:status 200
;                                      :body   {:total (+ x y)}})}}]
;       ["/post" {:post {:parameters {:query {:x int?}
;                                     :body   {:y int?}}
;                        :responses  {200 {:body {:total int?}}}
;                        :handler    (fn [{{{:keys [x]} :query} :parameters
;                                          {{:keys [y]} :body} :parameters}]
;                                      {:status 200
;                                       :body   {:total (+ x y)}})}}]
;       ["/plus/:z" {:post {:parameters {:query {:x int?}
;                                        :body {:y int?}
;                                        :path {:z int?}}
;                           :responses {200 {:body {:total int?}}}
;                           :handler (fn [{:keys [parameters]}]
;                                      (let [total (+ (-> parameters :query :x)
;                                                     (-> parameters :body :y)
;                                                     (-> parameters :path :z))]
;                                        {:status 200
;                                         :body {:total total}}))}}]]
