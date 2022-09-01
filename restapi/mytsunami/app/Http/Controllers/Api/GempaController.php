<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Models\Gempa;
use Illuminate\Http\Request;

class GempaController extends Controller
{
    public function dataGempa()
    {
        $now = now();
        $mulai = date("Y-m-d",strtotime($now))." 00:59:00";
        $akhir = date("Y-m-d",strtotime($now))." 23:59:00";
        $gempa = Gempa::where('magnitudo','>=',7)
            ->whereBetween('created_at',[$mulai,$akhir])
            ->where('isLaut',1)->where('kedalaman','<=',10)->first();
        if($gempa !== null){
            return response()->json([
                'status' => true,
                'message' => "Data berhasil didapatkan",
                'lat' =>  $gempa->lat,
                'long' =>  $gempa->long,
                'kedalaman' =>  $gempa->kedalaman,
                'magnitudo' =>  $gempa->magnitudo,
            ], 200);
        } else {
            return response()->json([
                'status' => false,
                'message' => "Data kosong!",
            ], 401);
        }
    }
}
