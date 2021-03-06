$(document).ready(
		function() {
			var mapObject = $('div#map');
			$('#mapCol').ready(
					function() {
						var lat = mapObject.data('latitude');
						var lon = mapObject.data('longitude');
						var zoom = 17;

						var fromProjection = new OpenLayers.Projection(
								"EPSG:4326");
						var toProjection = new OpenLayers.Projection(
								"EPSG:900913");
						var position = new OpenLayers.LonLat(lon, lat)
								.transform(fromProjection, toProjection);

						map = new OpenLayers.Map("map");
						var mapnik = new OpenLayers.Layer.OSM();
						map.addLayer(mapnik);

						var markers = new OpenLayers.Layer.Markers("Markers");
						map.addLayer(markers);
						markers.addMarker(new OpenLayers.Marker(position));

						map.setCenter(position, zoom);
					});
		});