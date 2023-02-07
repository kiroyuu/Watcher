#include "measurements.h"
#include <iostream>
#include <vector>

namespace Watcher {

Measurements::Measurements() : Measurements(3) {}
Measurements::Measurements(size_t size) {
}

Measurements::~Measurements() {
  //
}

measurement_t Measurements::getAverage() const {
  measurement_t average;
  
  for (auto measurement : _measurements_list) {
    average.temperature += measurement.temperature;
    average.humidity += measurement.humidity;
    average.luminosity += measurement.luminosity;
  }

  size_t measurements_amount = _measurements_list.size();
  average.temperature = average.temperature / measurements_amount;
  average.humidity = average.humidity / measurements_amount;
  average.luminosity = average.luminosity / measurements_amount;

  return average;
}

void Measurements::clear() {
  _measurements_list.clear();
}

void Measurements::addMeasurement(measurement_t &measurement) {
  _measurements_list.emplace_back(measurement);
}
}
