<?php
function random_string( $length ) {
$chars = "abcdefghijklmnopqrstuvwxyz0123456789";
$size = strlen( $chars );
for( $i = 0; $i < $length; $i++ ) {
$str .= $chars[ rand( 0, $size - 1 ) ];
 }
return $str;
}

$transRef = random_string(10);
?>