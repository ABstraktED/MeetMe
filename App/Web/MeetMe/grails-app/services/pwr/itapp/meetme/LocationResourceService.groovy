package pwr.itapp.meetme

import org.grails.jaxrs.provider.DomainObjectNotFoundException

class LocationResourceService {

    def create(Location dto) {
        dto.save()
    }

    def read(id) {
        def obj = Location.get(id)
        if (!obj) {
            throw new DomainObjectNotFoundException(Location.class, id)
        }
        obj
    }

    def readAll() {
        Location.findAll()
    }

    def update(Location dto) {
        def obj = Location.get(dto.id)
        if (!obj) {
            throw new DomainObjectNotFoundException(Location.class, dto.id)
        }
        obj.properties = dto.properties
        obj
    }

    void delete(id) {
        def obj = Location.get(id)
        if (obj) {
            obj.delete()
        }
    }
}
