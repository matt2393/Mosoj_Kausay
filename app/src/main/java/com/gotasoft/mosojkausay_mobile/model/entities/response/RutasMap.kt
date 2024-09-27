package com.gotasoft.mosojkausay_mobile.model.entities.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RutasMap(
    var geocoded_waypoints: List<GeocodedWaypoint> = listOf(),
    var routes: List<Route> = listOf(),
    var status: String = ""
) {
    @JsonClass(generateAdapter = true)
    data class GeocodedWaypoint(
        var geocoder_status: String = "",
        var place_id: String = "",
        var types: List<String> = listOf()
    )
    @JsonClass(generateAdapter = true)
    data class Route(
        var bounds: Bounds = Bounds(),
        var copyrights: String = "",
        var legs: List<Leg> = listOf(),
        var overview_polyline: OverviewPolyline = OverviewPolyline(),
        var summary: String = "",
        var warnings: List<String> = listOf(),
        var waypoint_order: List<Any> = listOf()
    ) {
        @JsonClass(generateAdapter = true)
        data class Bounds(
            var northeast: Northeast = Northeast(),
            var southwest: Southwest = Southwest()
        ) {
            @JsonClass(generateAdapter = true)
            data class Northeast(
                var lat: Double = 0.0,
                var lng: Double = 0.0
            )

            @JsonClass(generateAdapter = true)
            data class Southwest(
                var lat: Double = 0.0,
                var lng: Double = 0.0
            )
        }
        @JsonClass(generateAdapter = true)
        data class Leg(
            var distance: Distance = Distance(),
            var duration: Duration = Duration(),
            var end_address: String = "",
            var end_location: EndLocation = EndLocation(),
            var start_address: String = "",
            var start_location: StartLocation = StartLocation(),
            var steps: List<Step> = listOf(),
            var traffic_speed_entry: List<Any> = listOf(),
            var via_waypoint: List<Any> = listOf()
        ) {
            @JsonClass(generateAdapter = true)
            data class Distance(
                var text: String = "",
                var value: Int = 0
            )
            @JsonClass(generateAdapter = true)
            data class Duration(
                var text: String = "",
                var value: Int = 0
            )
            @JsonClass(generateAdapter = true)
            data class EndLocation(
                var lat: Double = 0.0,
                var lng: Double = 0.0
            )
            @JsonClass(generateAdapter = true)
            data class StartLocation(
                var lat: Double = 0.0,
                var lng: Double = 0.0
            )
            @JsonClass(generateAdapter = true)
            data class Step(
                var distance: Distance = Distance(),
                var duration: Duration = Duration(),
                var end_location: EndLocation = EndLocation(),
                var html_instructions: String = "",
                var maneuver: String = "",
                var polyline: Polyline = Polyline(),
                var start_location: StartLocation = StartLocation(),
                var travel_mode: String = ""
            ) {
                @JsonClass(generateAdapter = true)
                data class Distance(
                    var text: String = "",
                    var value: Int = 0
                )
                @JsonClass(generateAdapter = true)
                data class Duration(
                    var text: String = "",
                    var value: Int = 0
                )
                @JsonClass(generateAdapter = true)
                data class EndLocation(
                    var lat: Double = 0.0,
                    var lng: Double = 0.0
                )
                @JsonClass(generateAdapter = true)
                data class Polyline(
                    var points: String = ""
                )
                @JsonClass(generateAdapter = true)
                data class StartLocation(
                    var lat: Double = 0.0,
                    var lng: Double = 0.0
                )
            }
        }
        @JsonClass(generateAdapter = true)
        data class OverviewPolyline(
            var points: String = ""
        )
    }
}