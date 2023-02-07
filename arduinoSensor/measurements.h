#ifndef Measurements_h
#define Measurements_h

#include <iostream>
#include <chrono>
#include <vector>

namespace Watcher {

struct measurement_t {
  double temperature, humidity, luminosity;
};

class Measurements {
public:
  Measurements();
  Measurements(size_t size);
  ~Measurements();

  measurement_t getAverage() const; 
  void clear();
  void addMeasurement(measurement_t &measurement);

private:
  std::vector<measurement_t> _measurements_list;
};
}
#endif
