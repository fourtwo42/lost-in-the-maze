///httpRequest(url, method, header_map, body)
//Fixes http_request for the Android export
if (os_type == os_android) {
    return jhttp_request(argument0, argument1, json_encode(argument2), argument3);
} else {
    return http_request(argument0, argument1, argument2, argument3);
}
