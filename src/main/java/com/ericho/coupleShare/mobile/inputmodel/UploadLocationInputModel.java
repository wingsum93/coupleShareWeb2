package com.ericho.coupleShare.mobile.inputmodel;

import java.util.Date;
import java.util.List;

public class UploadLocationInputModel  extends BaseAuthModel{
	private List<Location> locations;

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public List<Location> getLocations() {
		return locations;
	}

	public static class Location {
        private String username;
        private Double latitude;

        private Double longitude;
        private Double accuracy;
        private Date date;

        public void setUsername(String username) {
            this.username = username;
        }

        public void setLatitude(Double latitude) {
            this.latitude = latitude;
        }

        public void setLongitude(Double longitude) {
            this.longitude = longitude;
        }

        public void setAccuracy(Double accuracy) {
            this.accuracy = accuracy;
        }

        public void setDate(Date date) {
            this.date = date;
        }

		public String getUsername() {
			return username;
		}

		public Double getLatitude() {
			return latitude;
		}

		public Double getLongitude() {
			return longitude;
		}

		public Double getAccuracy() {
			return accuracy;
		}

		public Date getDate() {
			return date;
		}
        
    }
	
}
