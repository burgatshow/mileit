$(document).ready(
		function() {
			$(function() {
				$('#color').colorpicker({
					"format" : "hex"
				});
			});

			$('#startDate').datepicker({
				format : "yyyy-mm-dd",
				startDate : "2000-01-01",
				todayHighlight : true,
				weekStart: 1
			});

			$('#endDate').datepicker({
				format : "yyyy-mm-dd",
				startDate : "2000-01-01",
				todayHighlight : true,
				weekStart: 1
			});

			$('#refuelDate').datepicker({
				format : "yyyy-mm-dd",
				todayHighlight : true,
				endDate: new Date(),
				weekStart: 1
			});
			
			$('#maintenanceDate').datepicker({
				format : "yyyy-mm-dd",
				todayHighlight : true,
				endDate: new Date(),
				weekStart: 1
			});
			
			$('#purchaseDate').datepicker({
				format : "yyyy-mm-dd",
				todayHighlight : true,
				endDate: new Date(),
				weekStart: 1
			});
			
			$('#changeDate').datepicker({
				format : "yyyy-mm-dd",
				todayHighlight : true,
				endDate: new Date(),
				weekStart: 1
			});
			
			$('#routeDatetime').datepicker({
				format : "yyyy-mm-dd",
				todayHighlight : true,
				endDate: new Date(),
				weekStart: 1
			});

			$('#confirm-archive').on(
					'show.bs.modal',
					function(e) {
						$(this).find('.btn-ok').attr('href',
								$(e.relatedTarget).data('href'));
					});
			
			$('#confirm-delete').on(
					'show.bs.modal',
					function(e) {
						$(this).find('.btn-ok').attr('href',
								$(e.relatedTarget).data('href'));
					});

			$(function() {
				$('[data-toggle="tooltip"]').tooltip()
			})

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