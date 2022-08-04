<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Gempa extends Model
{
    use HasFactory;
    protected $fillable =[
      'lat','long','kedalaman','magnitudo'
    ];
}
