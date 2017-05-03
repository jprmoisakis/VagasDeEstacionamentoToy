package exemplodaauladetestes

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class VagaController {

        static allowedMethods = [update: "PUT"]//, delete: "DELETE"]
        // save: "POST" foi retirado porque dá problema com o cucumber, que
        // provavelmente simula a chamada dessa ação como um GET

        def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [vagaInstanceList: Vaga.list(params),
         vagaInstanceTotal: Vaga.count()]
    }

    def overview(Integer max) {
        list(max)
    }

    def select(Long id) {
        def vaga = Vaga.get(id)
        if (!vaga) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'vaga.label', default: 'Vaga'), id])
            redirect(action: "overview")
            return
        }
        vaga.select()
        if (!vaga.save(flush: true)) {
            flash.message = "Vaga não pode ser reservada! Problema interno na gravação!\n" + vaga.errors
        }
        redirect(action: "overview")
    }

    def book() {
        def vaga = Vaga.findByOcupada(false)
        if (vaga != null) {
            vaga.select()
            vaga.save(flush:true)
            flash.message = "Vaga reservada"
        } else {
            flash.message = "Vaga indisponível"
        }
        redirect(action: "overview")
    }

    def show(Vaga vagaInstance) {
        respond vagaInstance
    }

    def create() {
        respond new Vaga(params)       //  [vagaInstance: new Vaga(params)]
    }

    @Transactional
    def save() {
        def vagaInstance = new Vaga(params)
        if (!vagaInstance.save(flush: true)) {
            render(view: "create", model: [vagaInstance: vagaInstance])
            return
        }
        flash.message = message(code: 'default.created.message', args: [message(code: 'vaga.label', default: 'Vaga'), vagaInstance.id])
        redirect(action: "show", id: vagaInstance.id)
    }

    // o save acima foi adicionado, e este abaixado renomeado porque o cucumber
    // não consegue simular a passagem do parâmetro a partir das informações em params
    @Transactional
    def savee(Vaga vagaInstance) {
        if (vagaInstance == null) {
            notFound()
            return
        }

        if (vagaInstance.hasErrors()) {
            respond vagaInstance.errors, view:'create'
            return
        }

        vagaInstance.save flush:true

        flash.message = message(code: 'default.created.message', args: [message(code: 'vaga.label', default: 'Vaga'), vagaInstance.id])
        redirect vagaInstance

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'vaga.label', default: 'Vaga'), vagaInstance.id])
                redirect vagaInstance
            }
            '*' { respond vagaInstance, [status: CREATED] }
        }
    }

    def edit(Vaga vagaInstance) {
        respond vagaInstance
    }

    @Transactional
    def update(Vaga vagaInstance) {
        if (vagaInstance == null) {
            notFound()
            return
        }

        if (vagaInstance.hasErrors()) {
            respond vagaInstance.errors, view:'edit'
            return
        }

        vagaInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Vaga.label', default: 'Vaga'), vagaInstance.id])
                redirect vagaInstance
            }
            '*'{ respond vagaInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Vaga vagaInstance) {

        if (vagaInstance == null) {
            notFound()
            return
        }

        vagaInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Vaga.label', default: 'Vaga'), vagaInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'vaga.label', default: 'Vaga'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
