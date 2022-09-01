<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Models\User;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\Validator;
use Illuminate\Support\Str;

class LoginController extends Controller
{
    public function login(Request $request)
    {
        $rules = [
            'email' => 'required|email|max:255',
            'password' => 'required|max:255',
        ];
        $validator = Validator::make($request->all(), $rules);
        if ($validator->fails()) {
            return response()->json([
                'status' => false,
                'message' => "data tidak lengkap",
            ], 404);
        } else {
            $data = [
                'email'     => $request->input('email'),
                'password'  => $request->input('password'),
            ];
            Auth::attempt($data);
            if (Auth::check()) {
                return response()->json([
                    'status' => true,
                    'message' => "Berhasil",
                    'user_id' => Auth::user()->id,
                ], 200);
            } else {
                return response()->json([
                    'status' => false,
                    'message' => "Username atau email salah!",
                ], 401);
            }
        }
    }
}
