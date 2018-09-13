<?php


public function store(Request $request)
    {

Validator::extend('uniqueDeviceNamePerUser', function ($attribute, $value, $parameters) {
            
            return Device::where(["user_id" => Auth::id(), "name" => $value])->count() ==  0 ;

        }, __("You Already added this device name."));

        $validator = Validator::make($request->all(), [
            'name' => 'required|uniqueDeviceNamePerUser',
        ]);
		
		.
		.
		.
		.
		.
		.
		.
		.
		.
		
		
		