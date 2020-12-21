function getRules() {
    var style = new OpenLayers.Style(
        // the first argument is a base symbolizer
        // all other symbolizers in rules will extend this one
        {
            label: '${name}',
            fillColor: 'yellow',
            fillOpacity: 0.5,
            strokeColor: 'grey',
            strokeOpacity: 0.6,
            strokeWidth: 1,
            fontSize:12,
            fontColor:'darkblue',
            label: "${name}" // label will be foo attribute value
        },
        // the second argument will include all rules
        {
            rules: [
                new OpenLayers.Rule({
                    // a rule contains an optional filter
                    filter: new OpenLayers.Filter.Comparison({
                        type: OpenLayers.Filter.Comparison.EQUAL_TO,
                        property: "type", // the "foo" feature attribute
                        value: 1,

                    }),
                    // if a feature matches the above filter, use this symbolizer
                    symbolizer:{
                        fillColor: 'red'
                    }
                }),
                new OpenLayers.Rule({
                    filter: new OpenLayers.Filter.Comparison({
                        type: OpenLayers.Filter.Comparison.EQUAL_TO,
                        property: "type",
                        value: 2
                    }),
                    symbolizer: {
                        fillColor: 'yellow'
                    }
                })
            ]
        }
    );
    return style;
}