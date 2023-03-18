//创建PolylineCollection对象实例
this.polylineCollection = window.viewer.scene.primitives.add(
  new Cesium.PolylineCollection()
);
var _this = this;
var PolyLinePrimitive = (function () {
  function _(positions) {
    this.options = {
      show: params.show,
      material: Cesium.Material.fromType('Color', {
        color : params.color
      }),
      positions: Cesium.Ellipsoid.WGS84.cartographicArrayToCartesianArray([]),
      width: params.width || 2,
    };
    this.positions = positions;
    this._init();
  }

  _.prototype._init = function () {
    var _self = this;
    this.options.positions = Cesium.PolylinePipeline.generateCartesianArc({
      positions: Cesium.Cartesian3.fromDegreesArray(_self.positions),
    });
    //存在折线则删除
    if(_this._polyLine[params.id]){
      _this.polylineCollection.remove(_this._polyLine[params.id]);
    }
    var pLine = _this.polylineCollection.add(this.options)
    _this._polyLine[params.id] = pLine;
    _this._entitys[params.id].positionLine = pLine;
  };
  return _;
})();

